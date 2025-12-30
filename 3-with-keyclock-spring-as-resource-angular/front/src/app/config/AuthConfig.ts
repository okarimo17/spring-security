import { AuthConfig } from "angular-oauth2-oidc";

export const authConfig: AuthConfig = {
    issuer: 'http://localhost:8081/realms/spring-realm',
    redirectUri: window.location.origin,
    clientId: 'auth-res-client',
    responseType: 'code',
    strictDiscoveryDocumentValidation: true,
    scope: 'openid profile email offline_access',
    requireHttps: false,
    useSilentRefresh : true
    
}