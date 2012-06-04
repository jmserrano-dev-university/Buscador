#!/bin/bash

for i in  $(ls -b /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_en);
	do
		oldname="$i"
		echo $oldname
		newname="f1.htm"
		mv /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_en/$oldname /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_en/$newname
		/home/serrano/Rapidminer/scripts/rapidminer -f /home/serrano/Rapidminer/Repositorio/buscador_en.rmp
		mv /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_en/$newname /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_en/$oldname
		mv -i /home/serrano/NetBeansProjects/Buscador/Documentos/Procesados_en/result.htm /home/serrano/NetBeansProjects/Buscador/Documentos/Procesados_en/$oldname
	done
