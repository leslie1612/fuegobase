package org.chou.project.fuegobase.repository.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldProjection {

    private long id;

    private long documentId;

    private String name;

    private String keyType;

    private String keyName;

    private long valueId;

    private String valueName;

    private String valueType;

}
