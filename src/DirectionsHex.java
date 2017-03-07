public enum DirectionsHex {
    UPPERLEFT,
    UPPERRIGHT,
    LEFT,
    RIGHT,
    LOWERLEFT,
    LOWERRIGHT;

    DirectionsHex getNextClockwise(){
        switch(this) {
            case UPPERLEFT:
                return UPPERRIGHT;
            case UPPERRIGHT:
                return RIGHT;
            case RIGHT:
                return LOWERRIGHT;
            case LOWERRIGHT:
                return LOWERLEFT;
            case LOWERLEFT:
                return LEFT;
            case LEFT:
                return UPPERLEFT;
        }

        // Error result
        return DirectionsHex.LEFT;
    }
}
