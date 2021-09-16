package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.Organisation;
import de.contracktor.model.Permission;
import de.contracktor.model.Role;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    UserManager userManager;

    @GetMapping("/admin/role")
    public String getRoleManagePage(Model model) {
        // Data:
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }
        // Logic:

        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }

    @GetMapping("/admin/role/add")
    public String getAddRoleManagePage(Model model) {
        // Data:
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }
        // Logic:

        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }

    @PostMapping("/admin/role/add")
    public String getAddRoleManagePage(@RequestParam String roleName, @RequestParam int permissionId, @RequestParam int organisationId, Model model) {
        // Logic:
        Optional<Organisation> organisationOptional = organisationRepository.findById(organisationId);
        Organisation organisation = organisationOptional.get();
        Optional<Permission> permissionOptional = permissionRepository.findById(permissionId);
        Permission permission = permissionOptional.get();
        Role role = new Role(roleName, permission, organisation);
        @SuppressWarnings("unused")
		Role save = roleRepository.save(role);

        // Data:
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }

        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }

    @GetMapping("/admin/role/delete")
    public String getDeleteRoleManagePage(Model model) {
        // Data:
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }

        // Logic:

        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }

    @PostMapping("/admin/role/delete")
    public String getDeleteRoleManagePage(@RequestParam int deleteId, Model model) {
        // Data:
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        // Logic:
        roleRepository.deleteById(deleteId);
        List<Role> roles = roleRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }
        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }

    @GetMapping("/admin/role/edit")
    public String getEditRoleManagePage(Model model) {
        // Data:
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }
        // Logic:


        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }

    @PostMapping("/admin/role/edit")
    public String getEditRoleManagePage(@RequestParam int roleChangeId, @RequestParam String roleChangeName, @RequestParam int organisationChangeId, @RequestParam int permissionChangeId, Model model) {
        // Data:
        List<Role> roles = roleRepository.findAll();
        List<Permission> permissions = permissionRepository.findAll();
        List<Organisation> organisations = organisationRepository.findAll();

        if(!userManager.isCurrentUserAppAdmin()) {
            organisations = List.of(organisationRepository.findByOrganisationName(userManager.getCurrentOrganisation()));
        }
        // Logic:
        Optional<Organisation> organisationOptional = organisationRepository.findById(organisationChangeId);
        Organisation organisation = organisationOptional.get();
        Optional<Permission> permissionOptional = permissionRepository.findById(permissionChangeId);
        Permission permission = permissionOptional.get();
        Optional<Role> roleOptional = roleRepository.findById(roleChangeId);
        Role role = roleOptional.get();
        role.setRoleName(roleChangeName);
        role.setOrganisation(organisation);
        role.setPermission(permission);
        @SuppressWarnings("unused")
		Role save = roleRepository.save(role);

        System.out.println(roleChangeName+organisationChangeId+permissionChangeId);


        // Model:
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissions);
        model.addAttribute("organisations", organisations);

        return "role";
    }
}
