import os
import hashlib
import subprocess
import xml.etree.ElementTree as ET
from zipfile import ZipFile, ZIP_DEFLATED

POM_FILE = "pom.xml"
TARGET_DIR = "target"

ARTIFACT_SUFFIXES = [
    ".jar",
    "-sources.jar",
    "-javadoc.jar",
    ".pom"
]

def read_pom_info(pom_path):
    tree = ET.parse(pom_path)
    root = tree.getroot()

    ns = {'m': 'http://maven.apache.org/POM/4.0.0'}

    group_id = root.findtext("m:groupId", namespaces=ns)
    if group_id is None:
        # Se não estiver no <project>, busca do <parent>
        group_id = root.findtext("m:parent/m:groupId", namespaces=ns)

    artifact_id = root.findtext("m:artifactId", namespaces=ns)
    version = root.findtext("m:version", namespaces=ns)
    if version is None:
        version = root.findtext("m:parent/m:version", namespaces=ns)

    if not all([group_id, artifact_id, version]):
        raise ValueError("Não foi possível extrair groupId, artifactId ou version do pom.xml")

    print(f"📦 Detalhes extraídos do POM:\n → groupId: {group_id}\n → artifactId: {artifact_id}\n → version: {version}")
    return group_id, artifact_id, version

def gpg_sign(file_path):
    print(f"🔏 Assinando: {file_path}")
    subprocess.run(["gpg", "--yes", "--armor", "--detach-sign", "--local-user", "1C5E887072A55B88", file_path], check=True)

def generate_checksum(file_path, algo):
    hash_func = hashlib.md5() if algo == "md5" else hashlib.sha1()
    with open(file_path, "rb") as f:
        hash_func.update(f.read())
    with open(f"{file_path}.{algo}", "w") as f:
        f.write(hash_func.hexdigest())
    print(f"✅ {algo.upper()} gerado: {file_path}.{algo}")

def main():
    if not os.path.exists(POM_FILE):
        print("❌ pom.xml não encontrado na pasta atual.")
        return

    group_id, artifact_id, version = read_pom_info(POM_FILE)
    group_path = os.path.join(*group_id.split("."), artifact_id, version)
    base_name = f"{artifact_id}-{version}"
    zip_path = os.path.join(TARGET_DIR, f"{base_name}.zip")

    files_to_zip = []

    for suffix in ARTIFACT_SUFFIXES:
        filename = f"{base_name}{suffix}"
        file_path = os.path.join(TARGET_DIR, filename)

        if not os.path.exists(file_path):
            print(f"❌ Arquivo não encontrado: {file_path}")
            continue

        gpg_sign(file_path)
        generate_checksum(file_path, "md5")
        generate_checksum(file_path, "sha1")

        files_to_zip.extend([
            filename,
            f"{filename}.asc",
            f"{filename}.md5",
            f"{filename}.sha1"
        ])

    print(f"\n📦 Criando ZIP: {zip_path}")
    with ZipFile(zip_path, "w", ZIP_DEFLATED) as zipf:
        for fname in files_to_zip:
            abs_path = os.path.join(TARGET_DIR, fname)
            arcname = os.path.join(group_path, os.path.basename(fname))
            zipf.write(abs_path, arcname=arcname)
            print(f"➕ Incluído: {arcname}")

    print(f"\n✅ ZIP criado com sucesso: {zip_path}")

if __name__ == "__main__":
    main()
