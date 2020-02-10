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

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__J
  (JNIEnv * env, jclass this_class, jlong p) {
    return decode(p)->solve();
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__JI
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    CaDiCaL::Solver * solver = decode(p);
    solver->assume(lit);
    return solver->solve();
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__JII
  (JNIEnv * env, jclass this_class, jlong p, jint lit1, jint lit2) {
    CaDiCaL::Solver * solver = decode(p);
    solver->assume(lit1);
    solver->assume(lit2);
    return solver->solve();
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__JIII
  (JNIEnv * env, jclass this_class, jlong p, jint lit1, jint lit2, jint lit3) {
    CaDiCaL::Solver * solver = decode(p);
        solver->assume(lit1);
        solver->assume(lit2);
        solver->assume(lit3);
        return solver->solve();
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1solve__J_3I
  (JNIEnv * env, jclass this_class, jlong p, jintArray assumptions) {
    jsize array_length = env->GetArrayLength(assumptions);
        CaDiCaL::Solver * solver = decode(p);
        jint * array = env->GetIntArrayElements(assumptions, 0);
        for (int i = 0; i < array_length; i++) {
            solver->assume(array[i]);
        }
        env->ReleaseIntArrayElements(assumptions, array, 0);
        return solver->solve();
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

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause__JI
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    CaDiCaL::Solver * solver = decode(p);
    solver->add(lit);
    solver->add(0);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause__JII
  (JNIEnv * env, jclass this_class, jlong p, jint lit1, jint lit2) {
    CaDiCaL::Solver * solver = decode(p);
    solver->add(lit1);
    solver->add(lit2);
    solver->add(0);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause__JIII
  (JNIEnv * env, jclass this_class, jlong p, jint lit1, jint lit2, jint lit3) {
    CaDiCaL::Solver * solver = decode(p);
    solver->add(lit1);
    solver->add(lit2);
    solver->add(lit3);
    solver->add(0);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1add_1clause__J_3I
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

JNIEXPORT jboolean JNICALL Java_org_satwrapper_CadicalSolver_cadical_1frozen
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    return decode(p)->frozen(lit);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1freeze
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    decode(p)->freeze(lit);
  }

JNIEXPORT void JNICALL Java_org_satwrapper_CadicalSolver_cadical_1melt
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    decode(p)->melt(lit);
  }

JNIEXPORT jint JNICALL Java_org_satwrapper_CadicalSolver_cadical_1fixed
  (JNIEnv * env, jclass this_class, jlong p, jint lit) {
    return decode(p)->fixed(lit);
  }