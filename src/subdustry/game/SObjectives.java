package subdustry.game;

import arc.Core;
import mindustry.world.Block;

import static mindustry.game.Objectives.*;

public class SObjectives {

    public static class Scan implements Objective {
        public Block fragment;

        public Scan(Block fragment){
            this.fragment = fragment;
        }

        protected Scan(){}

        @Override
        public boolean complete(){
            return false; //The objective is mostly just visual
        }

        @Override
        public String display(){
            return Core.bundle.format("requirement.scan", fragment.localizedName);
        }
    }
}
