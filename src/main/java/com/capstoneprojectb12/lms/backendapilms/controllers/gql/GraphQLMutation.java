package com.capstoneprojectb12.lms.backendapilms.controllers.gql;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capstoneprojectb12.lms.backendapilms.controllers.gql.classes.ClassMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.hello.HelloMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.role.RoleMutation;
import com.capstoneprojectb12.lms.backendapilms.controllers.gql.user.UserMutation;

import lombok.RequiredArgsConstructor;

@Controller
@SchemaMapping(typeName = "Mutation")
@CrossOrigin(allowedHeaders = { "*" }, allowCredentials = "*")
@RequiredArgsConstructor
public class GraphQLMutation {
    private final HelloMutation helloMutation;
    private final RoleMutation roleMutation;
    private final UserMutation userMutation;
    private final ClassMutation classMutation;

    @SchemaMapping(field = "hello")
    public HelloMutation helloMutation() {
        return this.helloMutation;
    }

    @SchemaMapping(field = "role")
    public RoleMutation roleMutation() {
        return this.roleMutation;
    }

    @SchemaMapping(field = "user")
    public UserMutation userMutation() {
        return this.userMutation;
    }

    @SchemaMapping(field = "class")
    public ClassMutation classMutation() {
        return this.classMutation;
    }
}
