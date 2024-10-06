#include <jni.h>
#include <string>
#include <iostream>

using namespace std;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_investmentinconstruction_InteractionJSON_contractWithCJNI(JNIEnv *env, jobject thiz, jstring json) {


    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}