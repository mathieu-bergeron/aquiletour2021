// Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)
//
// This file is part of tutoriels4f5
//
// tutoriels4f5 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// tutoriels4f5 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with aquiletour.  If not, see <https://www.gnu.org/licenses/>

package ca.ntro.core;

public class Constants {
	
	public static final String LANG = "fr"; // FIXME
	
	public static final String INITIALIZATION_TASK_ID="initializationTask";
	
	public static final String SOCKET_PREFIX = "/_socket";
	public static final String HTTP_PREFIX = "/_http";
	public static final String MESSAGES_SEGMENT = "messages";
	public static final String MESSAGES_URL_PATH_SOCKET = SOCKET_PREFIX + "/" + MESSAGES_SEGMENT;
	public static final String MESSAGES_URL_PATH_HTTP = HTTP_PREFIX + "/" + MESSAGES_SEGMENT;
	public static final String RESOURCES_URL_PREFIX = "/_resources";
	public static final String MODELS_URL_PREFIX = "/_models";
	
	public static final int RANDOM_STRING_DEFAULT_LENGTH = 20;
}
