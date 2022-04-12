package project.myblog.documentation;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import project.myblog.config.TestWebConfig;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@Import(TestWebConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Documentation {
    @LocalServerPort
    private int port;

    private RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    protected RequestSpecification givenRestDocs(String identifier, ParameterDescriptor[] parameterDescriptors) {
        return RestAssured.given(this.spec).log().all()
                .filter(document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestParameters(parameterDescriptors)
                        )
                );
    }

    protected RequestSpecification givenRestDocs(String identifier, FieldDescriptor[] fieldDescriptors) {
        return RestAssured.given(this.spec).log().all()
                .filter(document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        relaxedResponseFields(fieldDescriptors)
                        )
                );
    }

    protected RequestSpecification givenRestDocs(String identifier, ParameterDescriptor[] parameterDescriptors, FieldDescriptor[] fieldDescriptors) {
        return RestAssured.given(this.spec).log().all()
                .filter(document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestParameters(parameterDescriptors),
                        relaxedResponseFields(fieldDescriptors)
                        )
                );
    }
}
