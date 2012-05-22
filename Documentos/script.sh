#!/bin/bash
clear
echo "Script para renombramiento masivo de archivos"

extt=html
nombre=fich
valor=0

for fichero in `ls Originales_es/`
do
#mv $fichero $nombre$valor.$extt
echo $fichero
let valor++
done
