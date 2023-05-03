package com.oshewo.panic.enums;

@SuppressWarnings("unused")
public enum IngredientState {
    UNPREPARED,
    PREPARED,
    NOT_APPLICABLE, // This is for ingredients that are not in need of preparation
    UNCUT,
    UNCUT_UNCOOKED,
//    UNCOOKED_UNCUT,
    COOKED_UNCUT,
    CUT,
    UNCOOKED,
    HALF_COOKED,
    HALF_COOKED_UNCUT,
    COOKED,
    OVERCOOKED;

    public String getString() {
        switch (this) {
            case HALF_COOKED:
                return "HALF_COOKED";
            case HALF_COOKED_UNCUT:
                return "HALF_COOKED_UNCUT";
            case UNCOOKED:
                return "UNCOOKED";
//            case UNCOOKED_UNCUT:
//                return "UNCOOKED_UNCUT";
            case UNCUT:
                return "UNCUT";
            case UNPREPARED:
                return "UNPREPARED";
            case UNCUT_UNCOOKED:
                return "UNCUT_UNCOOKED";
            case CUT:
                return "CUT";
            case COOKED:
                return "COOKED";
            case PREPARED:
                return "PREPARED";
            case OVERCOOKED:
                return "OVERCOOKED";
            case COOKED_UNCUT:
                return "COOKED_UNCUT";
            case NOT_APPLICABLE:
                return "NOT_APPLICABLE";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case UNCOOKED:
                return "Uncooked";
            case UNPREPARED:
                return "Unprepared";
            case UNCUT:
                return "Uncut";
            case UNCUT_UNCOOKED:
                return "Uncut and Uncooked";
            case CUT:
                return "Cut";
            case COOKED:
                return "Cooked";
            case PREPARED:
                return "Prepared";
            case OVERCOOKED:
                return "Overcooked";
            case HALF_COOKED:
                return "Half Cooked";
            case NOT_APPLICABLE:
                return "Not Applicable";
            default:
                return "Unable to return string of " + this;
        }
    }
}
