package com.insaic.common.code.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DynamicSpecifications {
	private static final ConversionService conversionService = new DefaultConversionService();

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz) {
		return new Specification<T>() {

			//@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (filters != null && !filters.isEmpty()) {

					List<Predicate> predicates = Lists.newArrayList();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						if (expression.getJavaType() == Date.class) { //这里对Date类型的数据进行特殊处理
							Date date = null;
							SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
							try {
								date = sf.parse(filter.value.toString());
							} catch (Exception e) {
								try {
									date = sf.parse(filter.value.toString());
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
							switch (filter.operator) {
							case EQ:
								predicates.add(builder.equal(expression, date));
								break;
							case GT:
								predicates.add(builder.greaterThan(expression, (Comparable) date));
								break;
							case LT:
								predicates.add(builder.lessThan(expression, (Comparable) date));
								break;
							case GTE:
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) date));
								break;
							case LTE:
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) date));
								
								break;
							}
						} else {
							// logic operator
							switch (filter.operator) {
							case EQ:
								predicates.add(builder.equal(expression, filter.value));
								break;
							case LIKE:
								predicates.add(builder.like(expression, "%" + filter.value + "%"));
								break;
							case GT:
								predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
								break;
							case LT:
								predicates.add(builder.lessThan(expression, (Comparable) filter.value));
								break;
							case GTE:
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case LTE:
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case NE:
								predicates.add(builder.notEqual(expression, (Comparable) filter.value));
								break;
							}
						}

					}

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}

				return builder.conjunction();
			}

		};
	}
}
