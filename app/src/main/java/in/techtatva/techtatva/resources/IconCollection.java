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
    private final int constructure = R.drawable.tt_constructure;
    private final int cosmiccon = R.drawable.tt_cosmicon;
    private final int cryptoss = R.drawable.tt_cryptoss;
    private final int electrific = R.drawable.tt_electrific;
    private final int epsilon = R.drawable.tt_epsilon;
    private final int gaming = R.drawable.tt_gaming;
    private final int kraftwagen = R.drawable.tt_kraftwagen;
    private final int mechanize = R.drawable.tt_mechanize;
    private final int mechatron = R.drawable.tt_mechatronics;
    private final int open = R.drawable.tt_open;
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
            case "constructure": return constructure;
            case "cosmiccon": return cosmiccon;
            case "cryptoss": return cryptoss;
            case "electrific": return electrific;
            case "epsilon": return epsilon;
            case "gaming": return gaming;
            case "kraftwagen": return kraftwagen;
            case "mechanize": return mechanize;
            case "mechatron": return mechatron;
            case "open category": return open;
            case "robotrek": return robotrek;
            case "turing": return turing;
            default: return R.mipmap.ic_launcher;
        }

    }


}
