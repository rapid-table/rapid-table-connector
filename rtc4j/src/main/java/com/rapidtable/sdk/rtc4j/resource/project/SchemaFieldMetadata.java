package com.rapidtable.sdk.rtc4j.resource.project;

public class SchemaFieldMetadata {
    private SchemaFieldMetadataType type;
    private String value;

    public SchemaFieldMetadata(SchemaFieldMetadataType type, String value) {
        this.type = type;
        this.value = value;
    }

    public SchemaFieldMetadata() {
    }

    public static SchemaFieldMetadata text(final String value) {
        return SchemaFieldMetadata.builder()
            .type(SchemaFieldMetadataType.text)
            .value(value)
            .build();
    }

    public static SchemaFieldMetadata number(final String value) {
        return SchemaFieldMetadata.builder()
            .type(SchemaFieldMetadataType.number)
            .value(value)
            .build();
    }

    public static SchemaFieldMetadata date(final String value) {
        return SchemaFieldMetadata.builder()
            .type(SchemaFieldMetadataType.date)
            .value(value)
            .build();
    }

    public static SchemaFieldMetadataBuilder builder() {
        return new SchemaFieldMetadataBuilder();
    }

    public SchemaFieldMetadataType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setType(SchemaFieldMetadataType type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SchemaFieldMetadata)) return false;
        final SchemaFieldMetadata other = (SchemaFieldMetadata) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SchemaFieldMetadata;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }

    public String toString() {
        return "SchemaFieldMetadata(type=" + this.getType() + ", value=" + this.getValue() + ")";
    }

    public static class SchemaFieldMetadataBuilder {
        private SchemaFieldMetadataType type;
        private String value;

        SchemaFieldMetadataBuilder() {
        }

        public SchemaFieldMetadataBuilder type(SchemaFieldMetadataType type) {
            this.type = type;
            return this;
        }

        public SchemaFieldMetadataBuilder value(String value) {
            this.value = value;
            return this;
        }

        public SchemaFieldMetadata build() {
            return new SchemaFieldMetadata(this.type, this.value);
        }

        public String toString() {
            return "SchemaFieldMetadata.SchemaFieldMetadataBuilder(type=" + this.type + ", value=" + this.value + ")";
        }
    }
}
