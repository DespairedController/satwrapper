/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_satwrapper_CadicalSolver */

#ifndef _Included_org_satwrapper_CadicalSolver
#define _Included_org_satwrapper_CadicalSolver
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_satwrapper_CadicalSolver
 * Method:    create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_org_satwrapper_CadicalSolver_create
  (JNIEnv *, jclass);

/*
 * Class:     org_satwrapper_CadicalSolver
 * Method:    delete
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_delete
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_satwrapper_CadicalSolver
 * Method:    cadical_solve
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__J
  (JNIEnv *, jclass, jlong);

/*
 * Class:     org_satwrapper_CadicalSolver
 * Method:    cadical_solve
 * Signature: (J[I)I
 */
JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__J_3I
  (JNIEnv *, jclass, jlong, jintArray);

/*
 * Class:     org_satwrapper_CadicalSolver
 * Method:    cadical_add_clause
 * Signature: (J[I)V
 */
JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause
  (JNIEnv *, jclass, jlong, jintArray);

/*
 * Class:     org_satwrapper_CadicalSolver
 * Method:    cadical_get_literals
 * Signature: (J)[Z
 */
JNIEXPORT jbooleanArray JNICALL Java_org_satwrapper_CadicalSolver_cadical_1get_1literals
  (JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
