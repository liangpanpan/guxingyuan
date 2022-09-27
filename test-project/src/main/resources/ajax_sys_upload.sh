#!/bin/sh

. ../common/ajax_common

if [ "${PALANG}" = "en" ]; then
	LANG_001="Invalid upgrade deadline, please contact the manufacturer to obtain the upgrade License!"
	LANG_002="There is not enough space in the root directory. Please clean up and try to upgrade the system!"
	LANG_003="There is not enough space in the ramdisk. Please clean up and try to upgrade the system!"
	LANG_004="Invalid system: version information file does not exist!"
	LANG_005="When unpacking the package, please contact the manufacturer to ensure the system package is correct!"
	LANG_006="The uploaded upgrade package is inconsistent with the current version!"
	LANG_007="Upgrade_WEB_Interface"
	LANG_008="Upgrade success"
	LANG_009="Package Error"
	LANG_010="Upgrade System"
	LANG_011="Error unpacking feature library"
	LANG_012="Invalid feature library: no version information"
	LANG_013="Feature library is not compatible with current version"
	LANG_014="Invalid feature library: feature code file does not exist"
	LANG_015="Invalid feature library: dictionary table does not exist"
	LANG_016="System inconsistency"
	LANG_017="Upgrade failed, please confirm the validity of license with the manufacturer!"
	LANG_018="Import License"
	LANG_019="Import Success"
fi


upable_check()
{
	for val in `${FLOWEYE} key info`
	do
		eval "L_${val}"
	done

	if [ "${L_cantupgrade}" = "1" ]; then
		[ -f ${CGI_file} ] && rm ${CGI_file}
		retjson 1 "${LANG_001:=��Ч���������ޣ�����ϵ���һ�ȡ����License!}"
	fi
}


build_upret()
{
	if [ "${PALANG}" = "en" ]; then
		OLD_BUILDREL=`echo ${OLD_BUILDREL} | sed -r 's/\(.*\)//g'`
		BUILDREL=`echo ${BUILDREL} | sed -r 's/\(.*\)//g'`
	fi

	printf "{"
	printf "\"curjos\":\"${OLD_JOS_RELEASE}\","
	printf "\"newjos\":\"${JOS_RELEASE}\","
	printf "\"curver\":\"R${OLD_ENVER}[${OLD_BUILDREL}]��Build date ${OLD_BUILDDATE}\","
	printf "\"newver\":\"R${ENVER}[${BUILDREL}]��Build date ${BUILDDATE}\""
	printf "}"
}


upload_syspkt()
{
	# restart monitor
	[ -d ${PGETC}/log/ ] && rm -rf ${PGETC}/log/
	${IPECTRL} stop monitor >/dev/null 2>&1
	${IPECTRL} start monitor >/dev/null 2>&1

	# verify the root size
	root_size=6000
	root_size=`df -k | awk '{if($6=="/")print $4}'`
	if [ ${root_size} -le 2000 ]; then
		rm -rf ${CGI_file}
		sync
		retjson 1 "${LANG_002��Ŀ¼�ռ䲻�㣬��������ٳ�������ϵͳ��}"
	fi

	# verify the ramdisk size
	ram_size=10000
	ram_size=`df -k | awk '{if($6=="/usr/ramdisk")print $4}'`
	if [ ${ram_size} -le 8000 ]; then
		rm -rf ${CGI_file}
		sync
		retjson 1 "${LANG_003:=RAMDISK�ռ䲻�㣬��������ٳ�������ϵͳ��}"
	fi

	# verify the archive
	fileok=`tar ztvf ${CGI_file} | grep urlencode`
	if [ "${fileok}" = "" ]; then
		rm -rf ${CGI_file}
		sync
		retjson 1 "�����������⣬UI�ļ�������"
	fi

	# give it more space for upgrading
	rm -rf ${RAMDISK}/admin/tmp/*
	mkdir -p ${RAMDISK}/sysupgrade
	rm -rf ${RAMDISK}/sysupgrade/*
	mkdir -p ${RAMDISK}/tmp
	rm -rf ${RAMDISK}/tmp/*

	errmsg=`tar zxf ${CGI_file} -C ${RAMDISK}/sysupgrade 2>&1`
	if [ "$?" != "0" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		rm -rf ${CGI_file}
		sync
		retjson 1 "${LANG_005:=��ѹ������ʱ�������⣬����ϵ����ȷ��ϵͳ������ȷ�ԣ�}"
	fi

	rm -rf ${CGI_file}	
	sync

	panabitinf=`find ${RAMDISK}/sysupgrade -name panabit.inf`
	if [ "${panabitinf}" = "" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_004:=��Ч��������:�汾��Ϣ���ļ������ڣ�}"
	fi

	password_file=`find ${RAMDISK}/sysupgrade -name ".htpasswd"`
	if [ "${password_file}" = "" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "�����������⣬�����ļ�ȱʧ"
	fi
	
	rm -rf ${password_file}

	. ${panabitinf}

	# Check BSD version
	curbsd=`uname -r | cut -d'-' -f1`
	curbsdmajor=`echo ${curbsd} | cut -d'.' -f1`
	pkgbsdmajor=`echo ${BSDVER} | cut -d'.' -f1`
	ostype=`uname`
	if [ "${curbsdmajor}" != "${pkgbsdmajor}" -a "${ostype}" != "Linux" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_006:=��������ϵͳ�汾��һ��}"
	fi
	if [ "${ostype}" = "Linux" -a ${pkgbsdmajor} -ge 8 ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_006:=��������ϵͳ�汾��һ��}"
	fi
	
	while read line
	do
		eval OLD_${line}
	done << EOF
`cat /usr/ramdisk/etc/panabit.inf`
EOF

	retjson 0 "OK" "`build_upret`"
}


upgrade_web()
{
	newrel="${BUILDREL}"
	. /etc/PG.conf
	. /usr/ramdisk/etc/panabit.inf
	if [ "${newrel}" != "${BUILDREL}" ]; then 
		rm -rf ${RAMDISK}/sysupgrade/*
		retjson 1 "${LANG_006:=��������ϵͳ�汾��һ��}"
	fi

	admindir=`find ${RAMDISK}/sysupgrade -name admin | head -1`
	pkgroot=`dirname ${admindir}`

	# do upgrade
	cp -Rf ${pkgroot}/admin/* ${PGPATH}/admin/

	cp -Rf ${PGPATH}/admin/* ${RAMDISK}/admin/
	rm -rf ${RAMDISK}/sysupgrade/
	sync

	touch ${RAMDISK}/tmp/sys_up_success

	WEB_LOGGER "${LANG_007:=����ϵͳ����}"
	retjson 0 "OK"
}


upgrade_sys()
{
	mkdir -p ${RAMDISK}/tmp
	rm -f ${RAMDISK}/tmp/sys_up_success

	kerneldir=`find ${RAMDISK}/sysupgrade -name kernel -maxdepth 2`
	admindir=`find ${RAMDISK}/sysupgrade -name admin -maxdepth 2`
	bindir=`find ${RAMDISK}/sysupgrade -name bin -maxdepth 2`
	userdir=`find ${RAMDISK}/sysupgrade -name webuser -maxdepth 3`

	if [ "${kerneldir}" = "" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_009:=������������}��NO_KERNEL_DIR"
	fi

	if [ "${admindir}" = "" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_009:=������������}��NO_ADMIN_DIR"
	fi

	if [ "${bindir}" = "" ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_009:=������������}��NO_BIN_DIR"
	fi

	# verify the package.
	if [ ! -f ${bindir}/panaos -o ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_009:=������������}��NO_PANAOS"
	fi

	if [ ! -f ${bindir}/floweye ]; then
		rm -rf ${RAMDISK}/sysupgrade/*
		sync
		retjson 1 "${LANG_009:=������������}��NO_FLOWEYE"
	fi

	pkgroot=`dirname ${kerneldir}`
	binroot=`dirname ${bindir}`
	admroot=`dirname ${admindir}`

	if [ "${pkgroot}" != "${binroot}" ]; then
		rm -rf ${RAMDISK}/sysupgrade
		sync
		retjson 1 "${LANG_009:=������������}��NO_BIN_DIR"
	fi

	if [ "${pkgroot}" != "${admroot}" ]; then
		rm -rf ${RAMDISK}/sysupgrade
		sync
		retjson 1 "${LANG_009:=������������}��NO_ADMIN_DIR"
	fi

	if [ -d ${pkgroot}/usr ]; then
		rm -rf ${RAMDISK}/sysupgrade
		sync
		retjson 1 "${LANG_009:=������������}��NO_USE_DIR"
	fi

	. /etc/PG.conf
	[ -d ${PGPATH}/usr ] && rm -Rf ${PGPATH}/usr

	# Save Passowrd
	ufile_num=`ls -l ${USER_DIR} | awk 'END{print NR}'`
	[ ${ufile_num} -gt 1 ] && rm -rf ${userdir}

	# Upgrede web only
	[ "${CGI_method}" = "web" ] && upgrade_web

	# Copy to PGPATH
	cp -Rf ${pkgroot}/* ${PGPATH}/
	rm -rf ${RAMDISK}/sysupgrade/
	sync

	# Upgrade core only
	if [ "${CGI_method}" = "core" ]; then
		cp -Rf /usr/ramdisk/admin/* ${PGPATH}/admin/
		cp -Rf /usr/ramdisk/bin/ipe_httpd ${PGPATH}/bin/ipe_httpd
	fi

	# Copy to ramdisk
	# Don't overwrite /usr/ramdisk/bin/dpi.so at this time.
	# backup the new dpi.so
	ren=0
	if [ -f ${PGPATH}/bin/dpi.so ]; then
		mv ${PGPATH}/bin/dpi.so ${PGPATH}/bin/dpi.so.new
		ren=1
	fi

	cp -Rf ${PGPATH}/* ${RAMDISK}/
	
	if [ ${ren} -eq 1 ]; then
		mv ${RAMDISK}/bin/dpi.so.new ${PGPATH}/bin/dpi.so
	fi
	sync

	mkdir -p "${PGETC}/log/"
	echo "upgrade_system yes" >> ${EVENTFILE}

	WEB_LOGGER "${LANG_010:=����ϵͳ}"
	retjson 0 "OK"
}


query_upsys_stat()
{
	if [ -f ${RAMDISK}/tmp/sys_up_success ]; then
		rm -f ${RAMDISK}/tmp/sys_up_success
		retjson 0 "${LANG_008:=�����ɹ�}"
	else
		retjson 1 "RUN"
	fi
}


upload_dpi()
{
	# Get a clean directory
	mkdir -p ${RAMDISK}/upgrade
	rm -rf ${RAMDISK}/upgrade/*
	
	# Mv the file to the directory
	mv ${CGI_file} ${RAMDISK}/upgrade/sigdb.tar.gz
	cd ${RAMDISK}/upgrade
	errmsg=`tar zxf sigdb.tar.gz 2>&1`
	
	if [ "$?" != "0" ]; then
		cd -
		rm -rf ${RAMDISK}/upgrade/*
		retjson 1 "${LANG_011:=���������ʱ���ִ���}"
	fi
	
	rm -f ${RAMDISK}/upgrade/sigdb.tar.gz
	dpiinf=`find ./ -name dpi.inf`
	if [ "${dpiinf}" = "" ]; then
		cd -
		rm -rf ${RAMDISK}/upgrade/*
		retjson 1 "${LANG_012:=��Ч��������:�ް汾��Ϣ}"
	fi
	
	. ${dpiinf}
	curmach=`sysctl -n hw.machine`
	if [ "${MACHINE}" != "${curmach}" ]; then
		cd -
		rm -rf ${RAMDISK}/upgrade/*
		retjson 1 "${LANG_016:=ϵͳ��һ��}:${curmach}<->${MACHINE}"
	fi

	pamajor=`cat /usr/ramdisk/etc/panabit.inf | grep BUILDREL | cut -d'"' -f2 | cut -d'(' -f1`
	paminor=`cat /usr/ramdisk/etc/panabit.inf | grep BUILDREL | cut -d'"' -f2 | cut -d')' -f2`
	paversion="${pamajor}${paminor}"
	vermatch=0
	for ver in ${PALIST}; do
		if [ "${ver}" = "${paversion}" ]; then
			vermatch=1
			break
		fi
	done
	
	if [ ${vermatch} -eq 0 ]; then
		cd -
		rm -rf ${RAMDISK}/upgrade/*
		retjson 1 "${LANG_013:=�����ⲻ���ݵ�ǰ�汾}[${PALIST}->${paversion}]"
	fi

	mv ${dpiinf} ${RAMDISK}/upgrade/dpi.inf
	
	dpiso=`find ./ -name dpi.so`
	if [ "${dpiso}" = "" ]; then
		cd -
		rm -rf ${RAMDISK}/upgrade/*
		retjson 1 "${LANG_014:=��Ч��������:���������ļ�������}��"
	fi

	mv ${dpiso} ${RAMDISK}/upgrade/dpi.so
	
	dictso=`find ./ -name dict.so`
	if [ "${dictso}" = "" ]; then
		cd -
		rm -rf ${RAMDISK}/upgrade/*
		retjson 1 "${LANG_015:=��Ч��������:�ֵ������}!"
	fi

	mv ${dictso} ${RAMDISK}/upgrade/dict.so

	curver=`${FLOWEYE} dpi stat | grep version | cut -d"=" -f2`
	newver=`echo ${VERSION} | cut -d"_" -f2`

	retjson 0 "OK" "{\"newver\":\"${newver}\",\"curver\":\"${curver}\",\"curjos\":\"4\",\"newjos\":\"4\"}"
}


upgrade_dpi()
{
	mkdir -p ${RAMDISK}/tmp
	rm -f ${RAMDISK}/tmp/dpiupresult

	# Let monitor upgrade the system
	mkdir -p "${PGETC}/log/"
	echo "upgrade_sigdb yes" >> ${EVENTFILE}

	retjson 0 "OK"
}


query_updpi_stat()
{
	resfile=${RAMDISK}/tmp/dpiupresult
	
	if [ -f ${resfile} ]; then
		result=`cat ${resfile}`
		if [ "${result}" != "SUCCESS" ]; then
			retjson 2 "${result}"
		else
			rm -f  ${resfile}
			rm -rf ${RAMDISK}/upgrade/*
			sync
			retjson 0 "${LANG_008:=�����ɹ�}"
		fi
	else
		retjson 1 "RUN"
	fi
}


upload_key()
{
	mkdir -p ${RAMDISK}/upgrade
	rm -rf ${RAMDISK}/upgrade/*

	mv ${CGI_file} ${RAMDISK}/upgrade/license.dat
	license=`cat ${RAMDISK}/upgrade/license.dat`
	errmsg=`${FLOWEYE} key install ${license}`

	[ $? -ne 0 ] && retjson 1 "${LANG_017:=����ʧ�ܣ���ͬ����ȷ����Ȩ����Ч��!}"

	if [ -f ${PGETC}/license.dat ]; then
		mv ${PGETC}/license.dat ${PGETC}/license.dat.old
	fi

	mv ${RAMDISK}/upgrade/license.dat ${PGETC}/license.dat
	sync

	WEB_LOGGER "${LANG_018:=������Ȩ}"
	retjson 0 "${LANG_019:=����ɹ�}"
}


check_upgrade_env()
{
	rm -rf ${PGETC}/log/*

	/usr/ramdisk/bin/ipectrl stop monitor >/dev/null 2>&1
	/usr/ramdisk/bin/ipectrl start monitor >/dev/null 2>&1

	find ${RAMDISK} -name "*.apx" | xargs rm -rf
	find ${RAMDISK} -name "*.fetch" | xargs rm -rf
	find ${RAMDISK} -name "*.core" | xargs rm -rf
	find ${RAMDISK} -name "*core.*" | xargs rm -rf

	find ${PGPATH} -name "*.apx" | xargs rm -rf
	find ${PGPATH} -name "*.fetch" | xargs rm -rf
	find ${PGPATH} -name "*.core" | xargs rm -rf
	find ${PGPATH} -name "*core.*" | xargs rm -rf

	upgrade_cancel
}


upgrade_cancel()
{
	rm -rf ${RAMDISK}/sysupgrade/
	rm -rf ${RAMDISK}/upgrade/
	rm -rf ${RAMDISK}/appupgrade/

	sync
	retjson 0 "OK"
}


case "${CGI_action}" in

	"upload_syspkt")
		action_check
		upable_check
		upload_syspkt
		;;

	"upgrade_sys_yes")
		action_check
		upgrade_sys
		;;

	"query_upsys_stat")
		query_upsys_stat
		;;

	"upload_dpi")
		upable_check
		action_check
		upload_dpi
		;;

	"upgrade_dpi")
		action_check
		upgrade_dpi
		;;
	
	"query_updpi_stat")
		query_updpi_stat
		;;

	"upload_key")
		action_check
		upload_key
		;;

	"check_upgrade_env")
		action_check
		check_upgrade_env
		;;

	"upgrade_sys_cancel")
		action_check
		upgrade_cancel
		;;

	*)
		retjson 1 "UNKNOW_ACTION!"
		;;
esac
