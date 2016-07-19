package de.andre.service.commerce.order.processor;

public class PipelineConstants {
    private PipelineConstants() {
    }

    public static final String CHAIN_ORDER_PROCESS = "processOrder";
    public static final String CHAIN_ORDER_VALIDATE = "validateOrder";

    public static final String ORDER = "order";
    public static final String SITE = "site";
    public static final String LOCALE = "locale";
    public static final String ERROR_GENERIC = "processor.error.generic";
    public static final String ERROR_DEFAULT_DESCRIPTION = "Generic error";
}
