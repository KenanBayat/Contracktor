package de.contracktor.controller.dato;

import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class ProjectsDato {
    @Getter @Setter private List<Project> projects;

    private static ProjectsDato instance = new ProjectsDato();

    private ProjectsDato(){
        StateDato state = StateDato.getInstance();

        Organisation hochtief = new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland");
        Organisation züblin = new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland");
        Project p1 = new Project(1,"Hausbau", LocalDate.of(2000,8,8), LocalDate.of(2000,9,27), "Straße","4","Stadt","24106","Deutschland", 420.69, hochtief,"Günther", state.finished(), null, "house");
        Project p2 = new Project(2,"Bahnhof", LocalDate.of(2000,8,8),LocalDate.of(2004,7,14),"Street","7","Stadt","24106","Deutschland", 1337.69, züblin,"Eberhardt", state.finished(), null,"trains");
        Project p3 = new Project(3,"Berliner-Flughafen", LocalDate.of(2000,8,8),LocalDate.of(2098,11,30),"Alee","8","Stadt","24106","Deutschland", 999.99, züblin,"Sabine", state.inProgress(), null,"money");
        List<Project> projectList = List.of(
                p1,p2,p3
        );
        projects = projectList;
    }

    public static ProjectsDato getInstance(){
        return instance;
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
