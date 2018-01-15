package cz.chalupa.examples.texttransformer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TransformationRequest {

    @NotNull private final String value;
}
