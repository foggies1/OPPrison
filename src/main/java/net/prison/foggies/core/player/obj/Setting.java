package net.prison.foggies.core.player.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.prison.foggies.core.player.constant.SettingType;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Setting implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private SettingType settingType;
    private boolean toggled;

    public boolean toggle(){
        if(isToggled()){
            setToggled(false);
            return false;
        } else {
            setToggled(true);
            return true;
        }
    }



}
