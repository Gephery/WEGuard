package com.gmail.gephery.guard.controller;

/**
 * Created by maxgr on 7/15/2016.
 */
public class WEGText {

    public static String
    CREATE_CMD = "create",
    SELECT_CMD = "select",
    EDIT_CMD = "edit",

    CREATE_CMD_PERM = "WEG.create",
    SELECT_CMD_PERM = "WEG.select",
    EDIT_CMD_PERM = "WEG.edit",

    CREATE_CMD_USAGE = "/weg create <name>",
    SELECT_CMD_USAGE = "/weg select <p1:p2>",
    EDIT_CMD_USAGE = "/weg edit <region> <world> <attrabute> <att value>",

    SELECT_P1 = "p1",
    SELECT_P2 = "p2",

    SELECT_P1_MESSAGE = "Point 1 has been selected.",
    SELECT_P2_MESSAGE = "Point 2 has been selected.";
}
