package com.kangpei.cache.generator.impl;

import com.kangpei.cache.constants.CacheConstants;
import com.kangpei.cache.generator.IGenerator;
import com.kangpei.cache.utils.CacheUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.kangpei.cache.constants.CacheConstants.RETURN_VALUE;

/**
 * description: SpelKeyGenerator <br>
 * date: 2020/11/7 9:26 下午 <br>
 * author: kangpei <br>
 * version: 1.0 <br>
 */
@Component("spelKeyGenerator")
public class SpelKeyGenerator implements IGenerator {

    private static Method method = null;

    static {
        try {
            method = CacheUtils.class.getDeclaredMethod("isEmpty", Object.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("no such method isEmpty", e);
        }
    }

    @Override
    public String generate(String expression, Object[] arguments, Object retValue) {

        if (!expression.contains(CacheConstants.POUND_SIGN)
                && !expression.contains(CacheConstants.SINGLE_QUOTATION)) {
            return expression;
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("isEmpty", method);
        context.setVariable("args", arguments);
        if (expression.contains(RETURN_VALUE)) {
            context.setVariable("retVal", retValue);
        }
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression1 = expressionParser.parseExpression(expression);
        return expression1.getValue(context, String.class);
    }
}
