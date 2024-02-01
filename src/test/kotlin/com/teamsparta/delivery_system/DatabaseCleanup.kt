//package com.teamsparta.delivery_system
//
//import jakarta.persistence.*
//import jakarta.persistence.metamodel.EntityType
//import org.hibernate.mapping.Table
//import org.springframework.beans.factory.InitializingBean
//import org.springframework.stereotype.Component
//import org.springframework.transaction.annotation.Transactional
//import org.testcontainers.shaded.com.google.common.base.CaseFormat
//import java.util.function.Function
//import java.util.function.Predicate
//import java.util.stream.Collectors
//
//@Component
//class DatabaseCleanup : InitializingBean {
//    @PersistenceContext
//    private val entityManager: EntityManager? = null
//
//    private var tableNames: MutableList<String>? = null
//
//    override fun afterPropertiesSet() {
//        val entities: Set<EntityType<*>> = entityManager!!.metamodel.entities
//        tableNames = entities.stream()
//            .filter(Predicate<EntityType<*>> { e: EntityType<*> -> isEntity(e) && hasTableAnnotation(e) })
//            .map<String>(Function<EntityType<*>, String> { e: EntityType<*> ->
//                val tableName: String = e.getJavaType().getAnnotation(Table::class.java).name()
//                if (tableName.isBlank()) CaseFormat.UPPER_CAMEL.to(
//                    CaseFormat.LOWER_UNDERSCORE,
//                    e.getName()
//                ) else tableName
//            })
//            .collect(Collectors.toList<String>())
//
//        val entityNames = entities.stream()
//            .filter(Predicate<EntityType<*>> { e: EntityType<*> -> isEntity(e) && !hasTableAnnotation(e) })
//            .map<String>(Function<EntityType<*>, String> { e: EntityType<*> ->
//                CaseFormat.UPPER_CAMEL.to(
//                    CaseFormat.LOWER_UNDERSCORE,
//                    e.getName()
//                )
//            })
//            .toList()
//
//        tableNames.addAll(entityNames)
//    }
//
//    private fun isEntity(e: EntityType<*>): Boolean {
//        return null != e.getJavaType().getAnnotation(Entity::class.java)
//    }
//
//    private fun hasTableAnnotation(e: EntityType<*>): Boolean {
//        return null != e.getJavaType().getAnnotation(Table::class.java)
//    }
//
//    @Transactional
//    fun execute() {
//        entityManager!!.flush()
//        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
//
//        for (tableName in tableNames!!) {
//            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
//            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
//        }
//
//        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
//    }
//}