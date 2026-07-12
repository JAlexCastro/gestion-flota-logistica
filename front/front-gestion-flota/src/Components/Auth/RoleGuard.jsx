function RoleGuard({ roles, children }) {

    const rol = localStorage.getItem("rol");

    if (!roles.includes(rol)) {
        return null;
    }
    return children;
}

export default RoleGuard;