#!/bin/bash

for i in  $(ls -b /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_es);
	do
		oldname="$i"
		echo $oldname
		newname="f1.htm"
		mv /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_es/$oldname /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_es/$newname
		/home/serrano/Rapidminer/scripts/rapidminer -f /home/serrano/Rapidminer/Repositorio/buscador_es.rmp
		mv /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_es/$newname /home/serrano/NetBeansProjects/Buscador/Documentos/Originales_es/$oldname
		mv -i /home/serrano/NetBeansProjects/Buscador/Documentos/Procesados_es/result.htm /home/serrano/NetBeansProjects/Buscador/Documentos/Procesados_es/$oldname
	done

