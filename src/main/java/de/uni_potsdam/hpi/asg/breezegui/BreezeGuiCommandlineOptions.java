package de.uni_potsdam.hpi.asg.breezegui;

/*
 * Copyright (C) 2015 - 2017 Norman Kluge
 * 
 * This file is part of ASGbreezeGui.
 * 
 * ASGbreezeGui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ASGbreezeGui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ASGbreezeGui.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import de.uni_potsdam.hpi.asg.common.iohelper.CommandlineOptions;
import de.uni_potsdam.hpi.asg.common.misc.CommonConstants;

public class BreezeGuiCommandlineOptions extends CommandlineOptions {

    public boolean parseCmdLine(String[] args) {
        return super.parseCmdLine(args, "Usage: ASGbreezeGui [options] <breezefile>\nOptions:");
    }

    @Option(name = "-o", metaVar = "<level>", usage = "Outputlevel: 0:nothing\n1:errors\n2:+warnings\n[3:+info]")
    private int     outputlevel = 3;
    @Option(name = "-log", metaVar = "<logfile>", usage = "Define output Logfile, default is breezegui.log")
    private File    logfile     = new File(System.getProperty("user.dir"), "breezegui" + CommonConstants.LOG_FILE_EXTENSION);
    @Option(name = "-debug")
    private boolean debug       = false;

    @Option(name = "-mode", metaVar = "<mode>", usage = "Operationmode: [gui]:Show GUI\npng:Export png\nsvg:Export svg")
    private String  mode        = "gui";
    @Option(name = "-out", metaVar = "<file>", usage = "Outfile for png or svg operation mode. Default is out")
    private File    outfile     = new File(System.getProperty("user.dir"), "out");

    @Argument(metaVar = "Breezefile", required = true)
    private File    brezeefile;

    public int getOutputlevel() {
        return outputlevel;
    }

    public File getLogfile() {
        return logfile;
    }

    public boolean isDebug() {
        return debug;
    }

    public File getBrezeefile() {
        return brezeefile;
    }

    public String getMode() {
        return mode;
    }

    public File getOutfile() {
        return outfile;
    }
}
