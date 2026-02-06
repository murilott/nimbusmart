import os
import sys

# Caminho base onde criar a estrutura (pasta atual se não passar argumento)
BASE_DIR = sys.argv[1] if len(sys.argv) > 1 else "."

# Pacote base
PACKAGE_DIR = os.path.join(BASE_DIR, "src", "main", "java", "com", "example", "order")

# Lista de pastas a criar
folders = [
    os.path.join(PACKAGE_DIR, "application", "ports", "in"),
    os.path.join(PACKAGE_DIR, "application", "ports", "out"),
    os.path.join(PACKAGE_DIR, "application", "services"),
    os.path.join(PACKAGE_DIR, "application", "commands"),
    os.path.join(PACKAGE_DIR, "domain", "exception"),
    os.path.join(PACKAGE_DIR, "domain", "model", "order"),
    os.path.join(PACKAGE_DIR, "domain", "vo"),
    os.path.join(PACKAGE_DIR, "infrastructure", "messaging", "in"),
    os.path.join(PACKAGE_DIR, "infrastructure", "messaging", "out"),
    os.path.join(PACKAGE_DIR, "infrastructure", "persistance"),
    os.path.join(PACKAGE_DIR, "interfaces", "rest", "dto"),
    os.path.join(PACKAGE_DIR, "interfaces", "rest", "mapper"),
]

# Criar pastas
for folder in folders:
    os.makedirs(folder, exist_ok=True)

print(f"Estrutura de pastas criada com sucesso em {BASE_DIR}!")
