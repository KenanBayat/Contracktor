package de.contracktor.controller.dato;

import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import de.contracktor.model.State;
import de.contracktor.model.User;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProjectsDato {
    @Getter @Setter private List<Project> projects;

    public ProjectsDato(){
        Organisation hochtief = new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland");
        Organisation züblin = new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland");
        Project p1 = new Project("Hausbau", new Date(2000,9,27), "Straße","4","Stadt","24106","Deutschland", 420.69, hochtief,"Günther", new State("finished"),null, "house");
        Project p2 = new Project("Bahnhof",new Date(2004,7,14),"Street","7","Stadt","24106","Deutschland", 1337.69, züblin,"Eberhardt", new State("finished"),null,"trains");
        Project p3 =new Project("Berliner Flughafen",new Date(2099,11,31),"Alee","8","Stadt","24106","Deutschland", 9999.99, züblin,"Sabine", new State("in progress"),null,"money");
        List<Project> projectList = List.of(
                p1,p2,p3
        );
        projects = projectList;
    }

    public ProjectsDato(List<Project> projects){
        this.projects = projects;
    }

    public Project findById(int id){
        for(Project project: projects){
            if(id == project.getProjectID()){
                return project;
            }
        }
        return null;
    }

}
