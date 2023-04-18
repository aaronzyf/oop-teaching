package theater.annotations;

public @interface EnumField {
    Class<? extends Enum> value();
}