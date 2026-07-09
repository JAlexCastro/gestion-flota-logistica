import "./ConductorTable.css";

function ConductorTable({ conductores, onEdit, onDelete }) {
    const calcularDiasRestantes = (fecha) => {

    const hoy = new Date();

    const vencimiento = new Date(fecha);

    hoy.setHours(0, 0, 0, 0);
    vencimiento.setHours(0, 0, 0, 0);

    const diferencia = vencimiento - hoy;

    return Math.ceil(diferencia / (1000 * 60 * 60 * 24));

};
    return (

        <div className="table-container">

            <table className="conductor-table">

                <thead>
                    <tr>
                        <th>Rut</th>
                        <th>Nombre</th>
                        <th>Teléfono</th>
                        <th>N° Licencia</th>
                        <th>Clase Lic</th>
                        <th>Vencimiento</th>
                        <th>Dias Restantes</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>

                    {conductores.map((c) => (

                        <tr key={c.id}>

                            <td>{c.rut}</td>
                            <td>{c.nombre}</td>
                            <td>{c.telefono}</td>
                            <td>{c.numeroLicencia}</td>
                            <td>{c.claseLicencia}</td>
                            <td>{c.fechaVencimientoLicencia}</td>
                            <td>
                                {calcularDiasRestantes(c.fechaVencimientoLicencia)} días
                            </td>
                            <td>

                                <div className="actions">

                                    <button
                                        className="btn-edit"
                                        onClick={() => onEdit(c)}
                                    >
                                        Editar
                                    </button>

                                    <button
                                        className="btn-delete"
                                        onClick={() => onDelete(c.id)}
                                    >
                                        Eliminar
                                    </button>

                                </div>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

}

export default ConductorTable;