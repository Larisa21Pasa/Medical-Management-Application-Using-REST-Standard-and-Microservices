package com.projectmedicine.auth.AuthenticationComponent.protoComponents;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: authentication.proto")
public final class AuthenticationServiceGrpc {

  private AuthenticationServiceGrpc() {}

  public static final String SERVICE_NAME = "AuthenticationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto> getAuthenticateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "authenticate",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto> getAuthenticateMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto> getAuthenticateMethod;
    if ((getAuthenticateMethod = AuthenticationServiceGrpc.getAuthenticateMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getAuthenticateMethod = AuthenticationServiceGrpc.getAuthenticateMethod) == null) {
          AuthenticationServiceGrpc.getAuthenticateMethod = getAuthenticateMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "authenticate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("authenticate"))
                  .build();
          }
        }
     }
     return getAuthenticateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "register",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto> getRegisterMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto> getRegisterMethod;
    if ((getRegisterMethod = AuthenticationServiceGrpc.getRegisterMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getRegisterMethod = AuthenticationServiceGrpc.getRegisterMethod) == null) {
          AuthenticationServiceGrpc.getRegisterMethod = getRegisterMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("register"))
                  .build();
          }
        }
     }
     return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto> getLogoutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "logout",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto> getLogoutMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto> getLogoutMethod;
    if ((getLogoutMethod = AuthenticationServiceGrpc.getLogoutMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getLogoutMethod = AuthenticationServiceGrpc.getLogoutMethod) == null) {
          AuthenticationServiceGrpc.getLogoutMethod = getLogoutMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "logout"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("logout"))
                  .build();
          }
        }
     }
     return getLogoutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> getIsTokenValidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "isTokenValid",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> getIsTokenValidMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> getIsTokenValidMethod;
    if ((getIsTokenValidMethod = AuthenticationServiceGrpc.getIsTokenValidMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getIsTokenValidMethod = AuthenticationServiceGrpc.getIsTokenValidMethod) == null) {
          AuthenticationServiceGrpc.getIsTokenValidMethod = getIsTokenValidMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "isTokenValid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("isTokenValid"))
                  .build();
          }
        }
     }
     return getIsTokenValidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> getIsTokenValidByExpiredOrRevokedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "isTokenValidByExpiredOrRevoked",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> getIsTokenValidByExpiredOrRevokedMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> getIsTokenValidByExpiredOrRevokedMethod;
    if ((getIsTokenValidByExpiredOrRevokedMethod = AuthenticationServiceGrpc.getIsTokenValidByExpiredOrRevokedMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getIsTokenValidByExpiredOrRevokedMethod = AuthenticationServiceGrpc.getIsTokenValidByExpiredOrRevokedMethod) == null) {
          AuthenticationServiceGrpc.getIsTokenValidByExpiredOrRevokedMethod = getIsTokenValidByExpiredOrRevokedMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "isTokenValidByExpiredOrRevoked"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("isTokenValidByExpiredOrRevoked"))
                  .build();
          }
        }
     }
     return getIsTokenValidByExpiredOrRevokedMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getGetUserByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserById",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getGetUserByIdMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getGetUserByIdMethod;
    if ((getGetUserByIdMethod = AuthenticationServiceGrpc.getGetUserByIdMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getGetUserByIdMethod = AuthenticationServiceGrpc.getGetUserByIdMethod) == null) {
          AuthenticationServiceGrpc.getGetUserByIdMethod = getGetUserByIdMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "getUserById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("getUserById"))
                  .build();
          }
        }
     }
     return getGetUserByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getGetUserByEmailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUserByEmail",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getGetUserByEmailMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getGetUserByEmailMethod;
    if ((getGetUserByEmailMethod = AuthenticationServiceGrpc.getGetUserByEmailMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getGetUserByEmailMethod = AuthenticationServiceGrpc.getGetUserByEmailMethod) == null) {
          AuthenticationServiceGrpc.getGetUserByEmailMethod = getGetUserByEmailMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "getUserByEmail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("getUserByEmail"))
                  .build();
          }
        }
     }
     return getGetUserByEmailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto> getDeleteUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteUser",
      requestType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto.class,
      responseType = com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto,
      com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto> getDeleteUserMethod() {
    io.grpc.MethodDescriptor<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto> getDeleteUserMethod;
    if ((getDeleteUserMethod = AuthenticationServiceGrpc.getDeleteUserMethod) == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        if ((getDeleteUserMethod = AuthenticationServiceGrpc.getDeleteUserMethod) == null) {
          AuthenticationServiceGrpc.getDeleteUserMethod = getDeleteUserMethod = 
              io.grpc.MethodDescriptor.<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto, com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "AuthenticationService", "deleteUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto.getDefaultInstance()))
                  .setSchemaDescriptor(new AuthenticationServiceMethodDescriptorSupplier("deleteUser"))
                  .build();
          }
        }
     }
     return getDeleteUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthenticationServiceStub newStub(io.grpc.Channel channel) {
    return new AuthenticationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthenticationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AuthenticationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthenticationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AuthenticationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class AuthenticationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void authenticate(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getAuthenticateMethod(), responseObserver);
    }

    /**
     */
    public void register(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     */
    public void logout(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getLogoutMethod(), responseObserver);
    }

    /**
     */
    public void isTokenValid(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getIsTokenValidMethod(), responseObserver);
    }

    /**
     */
    public void isTokenValidByExpiredOrRevoked(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getIsTokenValidByExpiredOrRevokedMethod(), responseObserver);
    }

    /**
     */
    public void getUserById(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByIdMethod(), responseObserver);
    }

    /**
     */
    public void getUserByEmail(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getGetUserByEmailMethod(), responseObserver);
    }

    /**
     */
    public void deleteUser(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthenticateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto>(
                  this, METHODID_AUTHENTICATE)))
          .addMethod(
            getRegisterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getLogoutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto>(
                  this, METHODID_LOGOUT)))
          .addMethod(
            getIsTokenValidMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto>(
                  this, METHODID_IS_TOKEN_VALID)))
          .addMethod(
            getIsTokenValidByExpiredOrRevokedMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto>(
                  this, METHODID_IS_TOKEN_VALID_BY_EXPIRED_OR_REVOKED)))
          .addMethod(
            getGetUserByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto>(
                  this, METHODID_GET_USER_BY_ID)))
          .addMethod(
            getGetUserByEmailMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto>(
                  this, METHODID_GET_USER_BY_EMAIL)))
          .addMethod(
            getDeleteUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto,
                com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto>(
                  this, METHODID_DELETE_USER)))
          .build();
    }
  }

  /**
   */
  public static final class AuthenticationServiceStub extends io.grpc.stub.AbstractStub<AuthenticationServiceStub> {
    private AuthenticationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthenticationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthenticationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthenticationServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticate(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void register(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void logout(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void isTokenValid(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIsTokenValidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void isTokenValidByExpiredOrRevoked(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getIsTokenValidByExpiredOrRevokedMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserById(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserByEmail(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto request,
        io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthenticationServiceBlockingStub extends io.grpc.stub.AbstractStub<AuthenticationServiceBlockingStub> {
    private AuthenticationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthenticationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthenticationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthenticationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto authenticate(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getAuthenticateMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto register(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto logout(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getLogoutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto isTokenValid(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getIsTokenValidMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto isTokenValidByExpiredOrRevoked(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getIsTokenValidByExpiredOrRevokedMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto getUserById(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto getUserByEmail(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getGetUserByEmailMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto deleteUser(com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto request) {
      return blockingUnaryCall(
          getChannel(), getDeleteUserMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthenticationServiceFutureStub extends io.grpc.stub.AbstractStub<AuthenticationServiceFutureStub> {
    private AuthenticationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AuthenticationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthenticationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AuthenticationServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto> authenticate(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto> register(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto> logout(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getLogoutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> isTokenValid(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getIsTokenValidMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto> isTokenValidByExpiredOrRevoked(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getIsTokenValidByExpiredOrRevokedMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getUserById(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto> getUserByEmail(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getGetUserByEmailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto> deleteUser(
        com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE = 0;
  private static final int METHODID_REGISTER = 1;
  private static final int METHODID_LOGOUT = 2;
  private static final int METHODID_IS_TOKEN_VALID = 3;
  private static final int METHODID_IS_TOKEN_VALID_BY_EXPIRED_OR_REVOKED = 4;
  private static final int METHODID_GET_USER_BY_ID = 5;
  private static final int METHODID_GET_USER_BY_EMAIL = 6;
  private static final int METHODID_DELETE_USER = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthenticationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthenticationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE:
          serviceImpl.authenticate((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AuthenticationResponseProto>) responseObserver);
          break;
        case METHODID_REGISTER:
          serviceImpl.register((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.RegisterResponseProto>) responseObserver);
          break;
        case METHODID_LOGOUT:
          serviceImpl.logout((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.LogoutResponseProto>) responseObserver);
          break;
        case METHODID_IS_TOKEN_VALID:
          serviceImpl.isTokenValid((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto>) responseObserver);
          break;
        case METHODID_IS_TOKEN_VALID_BY_EXPIRED_OR_REVOKED:
          serviceImpl.isTokenValidByExpiredOrRevoked((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.AccessTokenRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.TokenValidityResponseProto>) responseObserver);
          break;
        case METHODID_GET_USER_BY_ID:
          serviceImpl.getUserById((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByIdRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto>) responseObserver);
          break;
        case METHODID_GET_USER_BY_EMAIL:
          serviceImpl.getUserByEmail((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.GetUserByEmailRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.UserResponseProto>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserRequestProto) request,
              (io.grpc.stub.StreamObserver<com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.DeleteUserResponseProto>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class AuthenticationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthenticationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.projectmedicine.auth.AuthenticationComponent.protoComponents.Authentication.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthenticationService");
    }
  }

  private static final class AuthenticationServiceFileDescriptorSupplier
      extends AuthenticationServiceBaseDescriptorSupplier {
    AuthenticationServiceFileDescriptorSupplier() {}
  }

  private static final class AuthenticationServiceMethodDescriptorSupplier
      extends AuthenticationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthenticationServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthenticationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthenticationServiceFileDescriptorSupplier())
              .addMethod(getAuthenticateMethod())
              .addMethod(getRegisterMethod())
              .addMethod(getLogoutMethod())
              .addMethod(getIsTokenValidMethod())
              .addMethod(getIsTokenValidByExpiredOrRevokedMethod())
              .addMethod(getGetUserByIdMethod())
              .addMethod(getGetUserByEmailMethod())
              .addMethod(getDeleteUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
