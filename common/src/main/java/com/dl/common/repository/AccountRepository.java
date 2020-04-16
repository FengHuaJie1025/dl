package com.dl.common.repository;

import com.dl.common.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA使用介绍： https://www.cnblogs.com/dreamroute/p/5173896.html
 *
 * JPA自动映射查询：
 * 表达式	            例子                              	hql查询语句
 * And	                findByLastnameAndFirstname	        … where x.lastname = ?1 and x.firstname = ?2
 * Or	                findByLastnameOrFirstname	        … where x.lastname = ?1 or x.firstname = ?2
 * Is,Equals	        findByFirstname,findByFirstnameIs,findByFirstnameEqual	… where x.firstname = 1?
 * Between	            findByStartDateBetween	            … where x.startDate between 1? and ?2
 * LessThan	            findByAgeLessThan	                … where x.age < ?1
 * LessThanEqual	    findByAgeLessThanEqual	            … where x.age ⇐ ?1
 * GreaterThan	        findByAgeGreaterThan	            … where x.age > ?1
 * GreaterThanEqual	    findByAgeGreaterThanEqual	        … where x.age >= ?1
 * After	            findByStartDateAfter	            … where x.startDate > ?1
 * Before	            findByStartDateBefore	            … where x.startDate < ?1
 * IsNull	            findByAgeIsNull	                    … where x.age is null
 * IsNotNull,NotNull	findByAge(Is)NotNull	            … where x.age not null
 * Like	                findByFirstnameLike	                … where x.firstname like ?1
 * NotLike	            findByFirstnameNotLike	            … where x.firstname not like ?1
 * StartingWith	        findByFirstnameStartingWith	        … where x.firstname like ?1 (parameter bound with appended %)
 * EndingWith	        findByFirstnameEndingWith	        … where x.firstname like ?1 (parameter bound with prepended %)
 * Containing	        findByFirstnameContaining	        … where x.firstname like ?1 (parameter bound wrapped in %)
 * OrderBy	            findByAgeOrderByLastnameDesc	    … where x.age = ?1 order by x.lastname desc
 * Not	                findByLastnameNot	                … where x.lastname <> ?1
 * In	                findByAgeIn(Collection ages)	    … where x.age in ?1
 * NotIn	            findByAgeNotIn(Collection age)	    … where x.age not in ?1
 * True	                findByActiveTrue()	                … where x.active = true
 * False	            findByActiveFalse()	                … where x.active = false
 * IgnoreCase	        findByFirstnameIgnoreCase	        … where UPPER(x.firstame) = UPPER(?1)
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

//    @Query(value = "from Account a where a.loginAccount = ?1")
//    Account getByLoginAccount(String loginAccount);

    List<Account> findByLoginAccount(String loginAccount);
}
