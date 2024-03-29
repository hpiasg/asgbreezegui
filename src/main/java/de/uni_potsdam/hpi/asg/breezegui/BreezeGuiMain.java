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
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;

import de.uni_potsdam.hpi.asg.breezegui.breezegraph.GuiMain;
import de.uni_potsdam.hpi.asg.common.breeze.model.AbstractBreezeNetlist;
import de.uni_potsdam.hpi.asg.common.breeze.model.BreezeProject;
import de.uni_potsdam.hpi.asg.common.iohelper.LoggerHelper;
import de.uni_potsdam.hpi.asg.common.iohelper.LoggerHelper.Mode;
import de.uni_potsdam.hpi.asg.common.iohelper.WorkingdirGenerator;

public class BreezeGuiMain {

    private static Logger                      logger;
    private static BreezeGuiCommandlineOptions options;

    public static void main(String[] args) {
        int status = main2(args);
        System.exit(status);
    }

    public static int main2(String[] args) {
        try {
            long start = System.currentTimeMillis();
            int status = -1;
            options = new BreezeGuiCommandlineOptions();
            if(options.parseCmdLine(args)) {
                logger = LoggerHelper.initLogger(options.getOutputlevel(), options.getLogfile(), options.isDebug(), Mode.cmdline);
                logger.debug("Args: " + Arrays.asList(args).toString());
                WorkingdirGenerator.getInstance().create(null, null, "guitmp");
                status = execute();
                WorkingdirGenerator.getInstance().delete();
            }
            long end = System.currentTimeMillis();
            if(logger != null) {
                logger.info("Runtime: " + LoggerHelper.formatRuntime(end - start, false));
            }
            return status;
        } catch(Exception e) {
            System.out.println("An error occurred: " + e.getLocalizedMessage());
            return 1;
        }
    }

    private static int execute() {

        File actualBreezeFile;
        try {
            actualBreezeFile = options.getBrezeefile().getCanonicalFile();
        } catch(IOException e) {
            logger.error(e.getLocalizedMessage());
            return -1;
        }

        BreezeProject proj = BreezeProject.create(actualBreezeFile, null, false, false);
        if(proj == null) {
            logger.error("Could not create Breeze project");
            return -1;
        }

        AbstractBreezeNetlist netlist = null;
        for(AbstractBreezeNetlist n : proj.getSortedNetlists()) {
            netlist = n;
        }
        if(netlist == null) {
            logger.error("Breeze file did not contain a netlist");
            return -1;
        }

        GuiMain gmain = new GuiMain(netlist, 1);

        switch(options.getMode()) {
            case "gui":
                gmain.show();
                while(!gmain.isClosed()) {
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {
                        logger.error(e.getLocalizedMessage());
                        return -1;
                    }
                }
                break;
            case "png":
                if(!gmain.exportPng(options.getOutfile())) {
                    return -1;
                }
                break;
            case "svg":
                if(!gmain.exportSvg(options.getOutfile())) {
                    return -1;
                }
                break;
            default:
                logger.error("Unknown mode: " + options.getMode());
                return -1;
        }

        return 0;

    }
}
