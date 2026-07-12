import { useState } from "react";
import RoleGuard from "../../components/Auth/RoleGuard";
import "./RevisionesPage.css";

import ListadoDocumentacion from "./ListadoDocumentacion";
import RegistrarDocumentacion from "./RegistrarDocumentacion";
import ActualizarDocumentacion from "./ActualizarDocumentacion";

function RevisionesPage() {

    const [vista, setVista] = useState("listar");

    return (

        <div className="revisiones-container">

            <h1 className="titulo-pagina">
                Documentación Vehículos
            </h1>

            <div className="toolbar-documentacion">

                <button
    className={vista === "listar" ? "toolbar-btn active" : "toolbar-btn"}
    onClick={() => setVista("listar")}
>
    Listar
</button>

<RoleGuard roles={["ADMIN", "OPERADOR"]}>

    <button
        className={vista === "registrar" ? "toolbar-btn active" : "toolbar-btn"}
        onClick={() => setVista("registrar")}
    >
        Registrar
    </button>

</RoleGuard>

<RoleGuard roles={["ADMIN", "OPERADOR"]}>

    <button
        className={vista === "actualizar" ? "toolbar-btn active" : "toolbar-btn"}
        onClick={() => setVista("actualizar")}
    >
        Actualizar
    </button>

</RoleGuard>
            </div>

            {
                vista === "listar" &&
                <ListadoDocumentacion />
            }

            {
                vista === "registrar" &&
                <RegistrarDocumentacion />
            }

            {
                vista === "actualizar" && (
                    <ActualizarDocumentacion />
                )
            }

        </div>
    );
}

export default RevisionesPage;