#/bin/bash
# 
# SalaryCalc: android app to help users convert salary from/to many formats.
# Copyright (C) 2014 Yankel Scialom.
# 
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software Foundation,
# Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
# 
# \author Yankel Scialom (YSC) <yankel.scialom@mail.com>
# \file ./src/eu/scialom/salarycalc/MainActivity.java
# 

echo "Auto Version: $(pwd)"

CODE=$(git tag | grep -c ^v[0-9])
NAME=$(git describe --always --dirty | sed -e 's/^v//')
COMMITS=$(echo ${NAME} | sed -e 's/[0-9\.]*//')

if [ -z "${COMMITS}" ] ; then
    VERSION="${NAME}"
else
    BRANCH=" ($(git branch | grep "^\*" | sed -e 's/^..//'))"
    VERSION="${NAME}${BRANCH}"
fi

echo -e "\tCode:\t${CODE}"
echo -e "\tVer:\t${VERSION}"

sed -i -e "s/android:versionCode=\"[0-9][0-9]*\"/android:versionCode=\"${CODE}\"/" \
	-e "s/android:versionName=\".*\"/android:versionName=\"${VERSION}\"/" \
	AndroidManifest.xml

exit 0