#include "../../../cadical/cadical-master/src/cadical.hpp"
#include "org_satwrapper_CadicalSolver.h"
#include <iostream>
static inline jlong encode(CaDiCaL::Solver* p) {
	return static_cast<jlong>(reinterpret_cast<intptr_t>(p));
}

static inline CaDiCaL::Solver* decode(jlong h) {
	return reinterpret_cast<CaDiCaL::Solver*>(static_cast<intptr_t>(h));
}

JNIEXPORT jlong JNICALL Java_org_satwrapper_CadicalSolver_create
  (JNIEnv * env, jclass this_class) {
    return encode(new CaDiCaL::Solver);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_delete
  (JNIEnv * env, jclass this_class, jlong p) {
    delete decode(p);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1val
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    decode(p)->add(lit);
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve
  (JNIEnv * env, jclass this_class, jlong p) {
    return decode(p)->solve();
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1val
  (JNIEnv * env, jclass this_class, jlong p, jint lit){
    return decode(p)->val(lit);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1assume
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    decode(p)->assume(lit);
  }

JNIEXPORT jboolean JNICALL Java_org_satwrapper_CadicalSolver_cadical_1failed
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    return decode(p)->failed(lit);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause
  (JNIEnv * env, jclass this_class, jlong p, jintArray literals) {
    jsize array_length = env->GetArrayLength(literals);
    CaDiCaL::Solver * solver = decode(p);
    jint * array = env->GetIntArrayElements(literals, 0);
    for (int i = 0; i < array_length; i++) {
        solver->add(array[i]);
    }
    solver->add(0);
    env->ReleaseIntArrayElements(literals, array, 0);
  }

JNIEXPORT jbooleanArray JNICALL Java_org_satwrapper_CadicalSolver_cadical_1get_1literals
  (JNIEnv * env, jclass this_class, jlong p) {
    jbooleanArray result;
    CaDiCaL::Solver * solver = decode(p);
    int size = solver->vars() + 1;
    result = env->NewBooleanArray(size);
    if (result == NULL) {
        return NULL;
    }
    jboolean literals[size];
    for (int i = 1; i < size; i++) {
        literals[i] = (solver->val(i) > 0);
    }
    env->SetBooleanArrayRegion(result, 0, size, literals);
    return result;
  }