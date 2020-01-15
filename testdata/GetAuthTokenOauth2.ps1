function fnGetOauthXSToken()
{
  param (
    [string]$ADFSOAUTHAuthorizeUri, 
    [string]$ADFSOAUTHGetTokenUri,
    [string]$Client_ID,
    [string]$Resource,
    [string]$Redirect_URI,
    [string]$RefreshToken,
    [string]$UserName,
    [string]$Password
  )

  if ( $RefreshToken -ne "" ) { 
    
    # Get OAUTH Access Token by using OAUTH refresh_token...

    ## Retrieve OAUTH Token...
    $vPostValues = "grant_type=refresh_token&client_id=" + $Client_ID + "&redirect_uri=" + $Redirect_URI + "&refresh_token=" + $RefreshToken
    $oResult0 = Invoke-RestMethod -Method Post -Uri $ADFSOAUTHGetTokenUri -Body $vPostValues -ContentType application/x-www-form-urlencoded

  } else {
    
    # Get OAUTH Access Token by using authorization_code (username and password)... 
  
    ## Build authentication Uri and create websession...
    $sUri = $ADFSOAUTHAuthorizeUri + "?response_type=code&client_id=" + $Client_ID + "&resource=" + $Resource + "&redirect_uri=" + $Redirect_URI
    $oWebSession = New-Object Microsoft.PowerShell.Commands.WebRequestSession

    ## Authenticate by using username and password (formbased)...
    $aPostValues = @{UserName=$UserName; Password=$Password; AuthMethod='FormsAuthentication' }
    $oResult0 = Invoke-WebRequest -Method Post -Uri $sUri -Body $aPostValues -Websession $oWebSession -MaximumRedirection 0 -ErrorAction SilentlyContinue

    ## Retrieve authorization code...
    $oResult0 = Invoke-WebRequest -Uri $sUri -Websession $oWebSession -MaximumRedirection 0 -ErrorAction SilentlyContinue
    $sCode = $oResult0.Headers.Location.Substring($oResult0.Headers.Location.IndexOf("?code=") + 6, ($oResult0.Headers.Location.Length - ($oResult0.Headers.Location.IndexOf("?code=") + 6) ) )
  
    ## Cleanup websession...
    $oResult0 = $null
    $oWebSession = $null
    $aPostValues = $null
  
    ## Retrieve OAUTH Token...
    $vPostValues = "grant_type=authorization_code&client_id=" + $Client_ID + "&redirect_uri=" + $Redirect_URI + "&code=" + $sCode
    $oResult0 = Invoke-RestMethod -Method Post -Uri $ADFSOAUTHGetTokenUri -Body $vPostValues -ContentType application/x-www-form-urlencoded
  
  }
  
  # Return Result and Cleanup...
  return $oResult0
  $vPostValues = $null
  $oResult0 = $null
}
 
fnGetOauthXSToken "https://adfs2.thisisglobal.com/adfs/oauth2/authorize" "https://adfs2.thisisglobal.com/adfs/oauth2/token" "gSchedule.test" "urn:gSchedule.test" "https://gscheduletest.thisisglobal.com/api/Management/Security/Authentication/GetAuthenticationToken" "" "SystemAdmin.Test@thisisglobal.com" "D0nkeyK0ng"