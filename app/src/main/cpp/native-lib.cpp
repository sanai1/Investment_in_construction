#include <jni.h>
#include <string>
#include <iostream>
#include "backend/contract.hpp"

using namespace std;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_investmentinconstruction_InteractionJSON_contractWithCJNI(JNIEnv *env, jobject thiz, jstring str) {

    const char *cparam = env->GetStringUTFChars(str, 0);

    string newJson = contract(cparam);

    const char *t = newJson.c_str();

    return env->NewStringUTF(t);
}