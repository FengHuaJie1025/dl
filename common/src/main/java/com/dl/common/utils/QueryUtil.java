package com.dl.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class QueryUtil {

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (Collections3.isNotEmpty(filters)) {

                    List<Predicate> predicates = new ArrayList<>();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }
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
                            case IN:
                                predicates.add(expression.in(filter.value));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }

    /**
     *
     * @param filters   door的查询条件的集合
     * @param <T>
     * @return
     */
//    public static <T> Specification<T> bySearchFilterDoor(final Collection<SearchFilter> filters) {
//        return new Specification<T>() {
//
//            @SuppressWarnings({ "unchecked", "rawtypes" })
//            @Override
//            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                if (Collections3.isNotEmpty(filters)) {
//
//                    List<Predicate> predicates = new ArrayList<>();
//                    for (SearchFilter filter : filters) {
//                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
//                        String[] names = StringUtils.split(filter.fieldName, ".");
//                        Path<String> expression = null;
//                        if(names[0].startsWith("[join]")){
//                            expression = root.join(names[0].substring(6));
//                        }else{
//                            expression = root.get(names[0]);
//                        }
//
//                        for (int i = 1; i < names.length; i++) {
//                            expression = expression.get(names[i]);
//                        }
//
//                        // logic operator
//                        switch (filter.operator) {
//                            case EQ:
//                                predicates.add(builder.equal(expression, filter.value));
//                                break;
//                            case LIKE:
//                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
//                                break;
//                            case GT:
//                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
//                                break;
//                            case LT:
//                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
//                                break;
//                            case GTE:
//                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
//                                break;
//                            case LTE:
//                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
//                                break;
//                            case ORLIKE:
//                                if(filter.value instanceof String){
//                                    String value = (String) filter.value;
//                                    String[] values = value.split(",");
//                                    Predicate[] like = new Predicate[values.length];
//                                    for(int i=0;i< values.length;i++){
//                                        like[i]=builder.like(expression, "%" + values[i] + "%");
//                                    }
//                                    predicates.add(builder.or(like));
//                                }
//                                break;
//                            case ANDLIKE:
//                                if(filter.value instanceof String){
//                                    String value = (String) filter.value;
//                                    String[] values = value.split(",");
//                                    Predicate[] like = new Predicate[values.length];
//                                    for(int i=0;i< values.length;i++){
//                                        like[i]=builder.like(expression, "%" + values[i] + "%");
//                                    }
//                                    predicates.add(builder.and(like));
//                                }
//                                break;
//                            case ANDNOTLIKE:
//                                if(filter.value instanceof String){
//                                    String value = (String) filter.value;
//                                    String[] values = value.split(",");
//                                    Predicate[] like = new Predicate[values.length];
//                                    for(int i=0;i< values.length;i++){
//                                        like[i]=builder.notLike(expression, "%" + values[i] + "%");
//                                    }
//                                    predicates.add(builder.and(like));
//                                }
//                                break;
//                            case OREQ:
//                                if(filter.value instanceof String){
//                                    String value = (String) filter.value;
//                                    String[] values = value.split(",");
//                                    Predicate[] like = new Predicate[values.length];
//                                    for(int i=0;i< values.length;i++){
//                                        like[i]=builder.equal(expression, values[i]);
//                                    }
//                                    predicates.add(builder.or(like));
//                                }
//                                break;
//                            case ANDNOTEQ:
//                                if(filter.value instanceof String){
//                                    String value = (String) filter.value;
//                                    String[] values = value.split(",");
//                                    Predicate[] like = new Predicate[values.length];
//                                    for(int i=0;i< values.length;i++){
//                                        like[i]=builder.notEqual(expression, values[i]);
//                                    }
//                                    predicates.add(builder.and(like));
//                                }
//                                break;
//                            case NOTEQ :
//                                predicates.add(builder.notEqual(expression,  filter.value));
//                                break;
//                            case NOTLIKE :
//                                predicates.add(builder.notLike(expression, "%" + filter.value + "%"));
//                                break;
//                            default:
//                                break;
//                        }
//
//                    }
//
//                    // 将所有条件用 and 联合起来
//                    if (!predicates.isEmpty()) {
//                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
//                    }
//                }
//
//                return builder.conjunction();
//            }
//        };
//    }

    public static <T> Specification<T> buildSpec(Class clazz, Map<String, String> map, String... fields) {

        return (Specification<T>) (root, query, criteriaBuilder) -> {
            Set<Predicate> predicates = new HashSet<>();
            List<Predicate> ps = new ArrayList<>(fields.length);

            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                switch (key) {
                    case "keyword":
                        String keyword = map.get("keyword").trim();

                        //通过反射获取所有属性
                        Field[] declaredFields = clazz.getDeclaredFields();
                        Set<String> declaredFieldNames = Arrays.stream(declaredFields).map(f -> f.getName()).collect(Collectors.toSet());

                        //将fields转成集合，求取交集
                        List<String> fieldss = Arrays.asList(fields);
                        fieldss.retainAll(declaredFieldNames);

                        for (String s : fieldss) {
                            reflactField(clazz, root, criteriaBuilder, keyword, ps, s);
                        }
                        Predicate[] buildPredicates = ps.toArray(new Predicate[0]);
                        predicates.add(criteriaBuilder.or(buildPredicates));
                        break;
                    case "start":
                    case "end":
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = simpleDateFormat.parse(map.get("start"));
                            Date endDate = simpleDateFormat.parse(map.get("end"));
                            predicates.add(criteriaBuilder.between(root.<Date>get("createTime"), startDate, endDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        String value = entry.getValue();
                        reflactField(clazz, root, criteriaBuilder, value, ps, key);
                        buildPredicates = ps.toArray(new Predicate[0]);
                        predicates.add(criteriaBuilder.or(buildPredicates));
                        break;
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private static <T> void reflactField(Class clazz, Root<T> root, CriteriaBuilder criteriaBuilder, String keyword, List<Predicate> ps, String fieldName) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            if (declaredField.getType() == Integer.class) {
                Predicate predicate = criteriaBuilder.equal(root.get(fieldName), Integer.parseInt(keyword));
                ps.add(predicate);
            } else if (declaredField.getType() == Long.class) {
                Predicate predicate = criteriaBuilder.equal(root.get(fieldName), Integer.parseInt(keyword));
                ps.add(predicate);
            } else if (declaredField.getType() == String.class) {
                Predicate predicate = criteriaBuilder.like(root.get(fieldName), "%" + keyword + "%");
                ps.add(predicate);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static <T> Specification<T> buildSpec(Map<String, String> map, String... fields) {

        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(map.get("keyword"))) {
                String keyword = "%" + map.get("keyword").trim() + "%";
                Predicate[] ps = new Predicate[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    String field = fields[i];
                    Predicate predicate = criteriaBuilder.like(root.get(field), keyword);
                    ps[i] = predicate;
                }
                predicates.add(criteriaBuilder.or(ps));
            }
            if (StringUtils.isNotBlank(map.get("start")) && StringUtils.isNotBlank(map.get("end"))) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startDate = simpleDateFormat.parse(map.get("start"));
                    Date endDate = simpleDateFormat.parse(map.get("end"));
                    predicates.add(criteriaBuilder.between(root.<Date>get("createTime"), startDate, endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Deprecated
    public static <T> Specification<T> buildSpec2(Class clazz, Map<String, String> map, String... fields) {

        return (Specification<T>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            List<Predicate> ps = new ArrayList<>(fields.length);

            if (map.containsKey("keyword")) {
                String keyword = map.get("keyword").trim();

                //通过反射获取所有属性
                Field[] declaredFields = clazz.getDeclaredFields();
                Set<String> declaredFieldNames = Arrays.stream(declaredFields).map(f -> f.getName()).collect(Collectors.toSet());

                //将fields转成集合，求取交集
                List<String> fieldss = Arrays.asList(fields);
                fieldss.retainAll(declaredFieldNames);

                for (String s : fieldss) {
                    reflactField(clazz, root, criteriaBuilder, keyword, ps, s);
                }
                Predicate[] buildPredicates = ps.toArray(new Predicate[0]);
                predicates.add(criteriaBuilder.or(buildPredicates));
            }
            if (map.containsKey("start") && map.containsKey("end")) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startDate = simpleDateFormat.parse(map.get("start"));
                    Date endDate = simpleDateFormat.parse(map.get("end"));
                    predicates.add(criteriaBuilder.between(root.<Date>get("createTime"), startDate, endDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (map.size() > 0) {
                Set<Map.Entry<String, String>> entries = map.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    reflactField(clazz, root, criteriaBuilder, value, ps, key);
                }
                Predicate[] buildPredicates = ps.toArray(new Predicate[0]);
                predicates.add(criteriaBuilder.or(buildPredicates));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


}