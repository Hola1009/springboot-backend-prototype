package ${packageName}.model.dto.${dataKey};

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 编辑${dataName}请求
 *
 * @author <a href="https://github.com/hola1009">fancier</a>
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ${upperDataKey}EditRequest {

    /**
    * id
    */
    private Long id;
}