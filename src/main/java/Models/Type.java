package Models;

public enum Type {
    TRANSPORTATION {
        @Override
        public String toString() {
            return "Transportation";
        }
    },
    FOOD {
        @Override
        public String toString() {
            return "Food";
        }
    },
    LODGING {
        @Override
        public String toString() {
            return "Lodging";
        }
    },
    MISCELLANEOUS {
        @Override
        public String toString() {
            return "Miscellaneous";
        }
    },
}
