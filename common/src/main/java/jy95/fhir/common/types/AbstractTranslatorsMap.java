package jy95.fhir.common.types;

import jy95.fhir.common.config.FDSConfig;
import java.util.Map;

public abstract class AbstractTranslatorsMap<C extends FDSConfig, D> {

    private final Map<DisplayOrder, AbstractTranslator<C, D>> translatorMap;

    public AbstractTranslatorsMap(Map<DisplayOrder, AbstractTranslator<C, D>> translatorMap) {
        this.translatorMap = translatorMap;
    }

    public AbstractTranslator<C, D> getTranslator(DisplayOrder displayOrder) {
        return translatorMap.get(displayOrder);
    }
}
