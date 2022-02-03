package net.prison.foggies.core.mines.obj;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Material;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MineBlock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String material;
    private double sellPrice;
    private long prestigeRequired;

    public Material toMat(){
        return Material.valueOf(material);
    }

}
