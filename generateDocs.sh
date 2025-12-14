#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Generazione diagrammi uml in .svg
find "$SCRIPT_DIR" -type f -name "*.puml" | while read -r file; do
    echo "Processing: $file"
    plantuml -tsvg "$file"
    plantuml -tpng "$file"
done

# Generazione PDF in Latex
cd "$SCRIPT_DIR/docs/latex"
pdflatex -shell-escape srs.tex
pdflatex -shell-escape design.tex

cp srs.pdf ../srs.pdf
cp design.pdf ../design.pdf
