package de.contracktor.controller.dato;

import de.contracktor.model.State;

public class StateDato {

    private static StateDato instance = new StateDato();

    private StateDato(){
    }

    public static StateDato getInstance(){
        return instance;
    }

    public State finished(){
        return finished;
    }

    public State inProgress(){
        return inProgress;
    }

    public State canceled(){
        return canceled;
    }

    private State finished = new State("finished");
    private State inProgress = new State("in progress");
    private State canceled = new State("canceled");


}
