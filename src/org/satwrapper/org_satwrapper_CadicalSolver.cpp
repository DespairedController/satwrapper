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
  (JNIEnv * env, jobject this_object) {
    return encode(new CaDiCaL::Solver);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_delete
  (JNIEnv * env, jobject this_object, jlong p) {
    delete decode(p);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1val
  (JNIEnv * env, jobject this_object, jlong p, jint lit) {
    decode(p)->add(lit);
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve
  (JNIEnv * env, jobject this_object, jlong p) {
    return decode(p)->solve();
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1val
  (JNIEnv * env, jobject this_object, jlong p, jint lit){
    return decode(p)->val(lit);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1assume
  (JNIEnv * env, jobject this_object, jlong p, jint lit) {
    decode(p)->assume(lit);
  }

JNIEXPORT jboolean JNICALL Java_org_satwrapper_CadicalSolver_cadical_1failed
  (JNIEnv * env, jobject this_object, jlong p, jint lit) {
    return decode(p)->failed(lit);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause
  (JNIEnv * env, jobject this_object, jlong p, jintArray literals) {
    jsize array_length = env->GetArrayLength(literals);
    CaDiCaL::Solver * solver = decode(p);
    jint * array = env->GetIntArrayElements(literals, 0);
    for (int i = 0; i < array_length; i++) {
        solver->add(array[i]);
    }
    solver->add(0);
    env->ReleaseIntArrayElements(literals, array, 0);
  }
