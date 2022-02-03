package net.prison.foggies.core.mines.obj;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.prison.foggies.core.utils.Cuboid;
import net.prison.foggies.core.utils.SimpleLocation;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class MineRegion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SimpleLocation spawnPoint, point1, point2, maxPoint1, maxPoint2;

    public boolean increaseMineRegion(int amount){
        Cuboid outerRegion = outerRegion();

        SimpleLocation right = new SimpleLocation(point1.clone().toBukkitLocation().add(amount, 0, amount));
        SimpleLocation left = new SimpleLocation(point2.clone().toBukkitLocation().subtract(amount, 0 ,amount));

        if(!outerRegion.contains(left.toBukkitLocation()) || !outerRegion.contains(right.toBukkitLocation())) {
            increaseMineRegion(amount-1);
            return false;
        }

        setPoint1(right);
        setPoint2(left);
        return true;
    }

    public CuboidRegion toCuboidRegion(){
        return new CuboidRegion(
                BlockVector3.at(point1.getBlockX(), point1.getBlockY(), point1.getBlockZ()),
                BlockVector3.at(point2.getBlockX(), point2.getBlockY(), point2.getBlockZ())
        );
    }

    public CuboidRegion toEntireCuboidRegion(){
        return new CuboidRegion(
                BlockVector3.at(maxPoint1.getBlockX(), maxPoint1.getBlockY(), maxPoint1.getBlockZ()),
                BlockVector3.at(maxPoint2.getBlockX(), maxPoint2.getBlockY(), maxPoint2.getBlockZ())
        );
    }

    public Cuboid innerRegion(){
        return new Cuboid(getPoint1().toBukkitLocation(), getPoint2().toBukkitLocation());
    }

    public Cuboid outerRegion(){
        return new Cuboid(maxPoint1.toBukkitLocation(), maxPoint2.toBukkitLocation());
    }

}
