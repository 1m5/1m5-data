package io.onemfive.data.health;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
@Indices({
        @Index(value = "did", type = IndexType.NonUnique)
})
public class ImpairmentTest implements Serializable {

    @Id
    private Long id;
    private Long did;


}
