package project.myblog.web.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NotNull(message = "제목은 null일 수 없습니다.")
@NotEmpty(message = "제목은 빈 값일 수 없습니다.")
@NotBlank(message = "제목은 공백일 수 없습니다.")
public @interface MemberBlank {
}
