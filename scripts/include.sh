# Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
#
# This file is part of aquiletour
#
# aquiletour is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# aquiletour is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

root_dir=$(dirname "$scripts_dir")
init_sh="init.sh"

aquiletour_jdk="$root_dir"/aquiletour_jdk
aquiletour_jsweet="$root_dir"/aquiletour_jsweet

resources_dir="$aquiletour_jdk"/src/main/resources
resources_dir_dev="$resources_dir"/dev
resources_dir_public="$resources_dir_dev"/public
js_dir_dev="$resources_dir_public"/js

jsweet_target_dir="$aquiletour_jsweet"/target
jsweet_js_dir="$jsweet_target_dir"/javascript
jsweet_candies_dir="$jsweet_target_dir"/candies
jsweet_js_file_dev="$jsweet_js_dir"/bundle.js
jsweet_map_file_dev="$jsweet_js_dir"/bundle.js.map
jsweet_js_file_prod="$jsweet_js_dir"/bundle.min.js

save_dir(){

    current_dir=$(pwd)
}

restore_dir(){

    cd "$current_dir"
}




