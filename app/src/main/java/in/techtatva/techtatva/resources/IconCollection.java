package in.techtatva.techtatva.resources;

import android.content.Context;

import in.techtatva.techtatva.R;

/**
 * Created by anurag on 3/10/16.
 */
public class IconCollection {

    private final int acumen = R.drawable.tt_acumen;
    private final int airborne = R.drawable.tt_aero;
    private final int alacrity = R.drawable.tt_alacrity;
    private final int bizzmaestro = R.drawable.tt_bizzmaestro;
    private final int cheminova = R.drawable.tt_cheminova;
    private final int chrysalis = R.drawable.tt_chrysalis;
    private final int conclave = R.drawable.tt_themanipalconclave;
    private final int constructure = R.drawable.tt_constructure;
    private final int cosmiccon = R.drawable.tt_cosmicon;
    private final int cryptoss = R.drawable.tt_cryptoss;
    private final int electrific = R.drawable.tt_electrific;
    private final int energia = R.drawable.tt_energia;
    private final int epsilon = R.drawable.tt_epsilon;
    private final int fuelRC = R.drawable.tt_fuelrc;
    private final int gaming = R.drawable.tt_gaming;
    private final int kraftwagen = R.drawable.tt_kraftwagen;
    private final int mechanize = R.drawable.tt_mechanize;
    private final int mechatron = R.drawable.tt_mechatronics;
    private final int open = R.drawable.tt_open;
    private final int paper = R.drawable.tt_paper;
    private final int qi = R.drawable.tt_questionable;
    private final int robowars = R.drawable.tt_robowars;
    private final int robotrek = R.drawable.tt_robotrek;
    private final int turing = R.drawable.tt_turing;

    public IconCollection() {
    }

    public int getIconResource(Context context, String catName){
        switch(catName.toLowerCase()){
            case "acumen": return acumen;
            case "alacrity": return alacrity;
            case "airborne": return airborne;
            case "bizzmaestro": return bizzmaestro;
            case "cheminova": return cheminova;
            case "chrysalis": return chrysalis;
            case "constructure": return constructure;
            case "cosmiccon": return cosmiccon;
            case "cryptoss": return cryptoss;
            case "electrific": return electrific;
            case "energia" : return energia;
            case "epsilon": return epsilon;
            case "featured event-paper presentation": return paper;
            case "fuel rc 5": return fuelRC;
            case "gaming": return gaming;
            case "kraftwagen": return kraftwagen;
            case "the manipal conclave": return conclave;
            case "mechanize": return mechanize;
            case "mechatron": return mechatron;
            case "open category": return open;
            case "questionable intelligence": return qi;
            case "robotrek": return robotrek;
            case "robowars": return robowars;
            case "turing": return turing;
            default: return R.mipmap.ic_launcher;
        }

    }


}
