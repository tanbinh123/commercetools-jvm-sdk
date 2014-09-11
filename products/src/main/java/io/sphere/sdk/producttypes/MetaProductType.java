package io.sphere.sdk.producttypes;

import io.sphere.sdk.attributes.AttributeDefinition;

import java.util.*;

/**
 * All product types merged into one.
 *
 * In SPHERE.IO different product types can be created but the attribute type for one attribute name must be consistent.
 * So this container can contain all attribute information for all product types.
 */
public interface MetaProductType extends AttributeDefinitionContainer {
    public static MetaProductType of(final List<AttributeDefinition> definitions) {
        return new MetaProductTypeImpl(definitions);
    }

    public static MetaProductType of(final Collection<? extends AttributeDefinitionContainer> definitionContainers) {
        final Map<String, AttributeDefinition> nameToDefinitionMap = new HashMap<>();
        definitionContainers.forEach(container ->
            container.getAttributes().forEach(definition ->
                nameToDefinitionMap.put(definition.getName(), definition)
            )
        );
        final List<AttributeDefinition> uniqueDefinitions = new ArrayList<>(nameToDefinitionMap.size());
        nameToDefinitionMap.forEach((name, definition) -> uniqueDefinitions.add(definition));
        return of(uniqueDefinitions);
    }
}
